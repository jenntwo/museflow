import OpenAI from "openai";
import { Router } from "express";
import { generateArtworkAnswer } from "../services/llmService.js";
import {
  getArtworkById,
  ArtworkNotFoundError,
} from "../services/artworkService.js";
import { buildConversation } from "../services/conversationService.js";
import { aiRateLimiter } from "../middleware/aiRateLimiter.js";

const router = Router();

interface ConversationMessage {
    role: "user" | "assistant";
    content: string;
  }
  
  interface ChatRequest {
    artworkId: string;
    message: string;
    history?: ConversationMessage[];
  }

router.post("/chat",aiRateLimiter, async (req, res) => {
    const { artworkId, message, history = [] } =
    (req.body ?? {}) as Partial<ChatRequest>;

  if (
    typeof artworkId !== "string" ||
    !artworkId.trim() ||
    typeof message !== "string" ||
    !message.trim()
  ) {
    res.status(400).json({
      error: "artworkId and message are required.",
    });
    return;
  }
  const isValidHistory =
  Array.isArray(history) &&
  history.length <= 20 &&
  history.every(
    (item): item is ConversationMessage =>
      typeof item === "object" &&
      item !== null &&
      (item.role === "user" || item.role === "assistant") &&
      typeof item.content === "string" &&
      item.content.trim().length > 0 &&
      item.content.length <= 4000
  );

if (!isValidHistory) {
  res.status(400).json({
    error: "Invalid conversation history.",
  });
  return;
}

  try {
    // Step 1: 从 Spring Boot 获取 Artwork
    const artwork = await getArtworkById(artworkId.trim());

    // Step 2: 生成结构化 Artwork Context
    const artworkContext = {
      title: artwork.title,
      artist: artwork.artist,
      yearCreated: artwork.yearCreated,
      medium: artwork.medium,
      description: artwork.description,
    };

    const conversation = buildConversation(
        artwork,
        history,
        message.trim()
      );

    // Step 3: 暂时返回基于数据库信息的 Mock Reply
    const demoMode = process.env.AI_DEMO_MODE === "true";

const reply = demoMode
  ? `Demo response for "${artwork.title}". ` +
    `Received ${history.length} previous messages. ` +
    `This is not an AI-generated answer.`
  : await generateArtworkAnswer(
      artwork,
      message.trim()
    );
    
    res.json({
        artworkId: artwork.id,
        artworkContext,
        reply,
        demoMode,
        conversationLength: conversation.length,
      });
      
  } catch (error) {
    if (error instanceof ArtworkNotFoundError) {
      res.status(404).json({
        error: "Artwork not found.",
      });
      return;
    }

    console.error("AI chat context error:", error);

    if (error instanceof OpenAI.APIError) {
        console.error("OpenAI API error:", {
          status: error.status,
          message: error.message,
        });
      
        res.status(502).json({
          error: "AI model is temporarily unavailable.",
        });
        return;
      }

    const isNetworkError =
      error instanceof TypeError ||
      (error instanceof Error && error.name === "TimeoutError");

    res.status(isNetworkError ? 503 : 502).json({
      error: "Unable to retrieve artwork context.",
    });
  }
});

export default router;
