import { useEffect, useRef, useState, type FormEvent } from "react";
import "./AIChat.css";
import { sendAIChatMessage } from "../api/ai";

const SUGGESTED_QUESTIONS = [
    "What inspired this artwork?",
    "What techniques did the artist use?",
    "Why is this artwork important?",
  ];


  const CHAT_STORAGE_PREFIX = "museflow-chat-";

  function loadMessages(artworkId: string): ChatMessage[] {
    try {
      const stored = sessionStorage.getItem(
        `${CHAT_STORAGE_PREFIX}${artworkId}`
      );
  
      if (!stored) return [];
  
      const parsed: unknown = JSON.parse(stored);
  
      if (!Array.isArray(parsed)) return [];
  
      return parsed.filter(
        (message): message is ChatMessage =>
          typeof message === "object" &&
          message !== null &&
          typeof message.id === "string" &&
          (message.role === "user" || message.role === "assistant") &&
          typeof message.content === "string" &&
          typeof message.createdAt === "number"
      );
    } catch {
      return [];
    }
  }
interface AIChatProps {
  artworkId: string;
  artworkTitle: string;
}

interface ChatMessage {
    id: string;
    role: "user" | "assistant";
    content: string;
    createdAt: number;
    status?: "sent" | "failed";
}

export default function AIChat({
  artworkId,
}: AIChatProps) {
  const [error, setError] = useState<string | null>(null);
  const [input, setInput] = useState("");
  const [messages, setMessages] = useState<ChatMessage[]>(
    () => loadMessages(artworkId));
  const [loading, setLoading] = useState(false);

  const messagesEndRef = useRef<HTMLDivElement | null>(null);

useEffect(() => {
  if (messages.length > 0 || loading) {
    messagesEndRef.current?.scrollIntoView({
      behavior: "smooth",
      block: "nearest",
    });
  }
}, [messages, loading]);

useEffect(() => {
    try {
      sessionStorage.setItem(
        `${CHAT_STORAGE_PREFIX}${artworkId}`,
        JSON.stringify(messages)
      );
    } catch (error) {
      console.warn("Unable to save chat history:", error);
    }
  }, [artworkId, messages]);

  // No API key yet: use Demo Mode.
  //const DEMO_MODE = false;

  async function sendMessage(
    question: string,
    retryMessageId?: string
  ) {
    const trimmedQuestion = question.trim();
  
    if (!trimmedQuestion || loading) return;
  
    setError(null);
    setLoading(true);
  
    const messageId = retryMessageId ?? crypto.randomUUID();
  
    const userMessage: ChatMessage = {
      id: messageId,
      role: "user",
      content: trimmedQuestion,
      createdAt: Date.now(),
      status: "sent",
    };
  
    if (!retryMessageId) {
      setMessages((prev) => [...prev, userMessage]);
    } else {
      setMessages((prev) =>
        prev.map((message) =>
          message.id === messageId
            ? { ...message, status: "sent" }
            : message
        )
      );
    }
  
    try {
      // Use only messages before the current question.
      const currentIndex = messages.findIndex(
        (message) => message.id === messageId
      );
  
      const previousMessages =
        retryMessageId && currentIndex >= 0
          ? messages.slice(0, currentIndex)
          : messages;
  
      const history = previousMessages
        .filter(
          (message) =>
            message.status !== "failed" &&
            (message.role === "user" ||
              message.role === "assistant")
        )
        .slice(-20)
        .map(({ role, content }) => ({
          role,
          content,
        }));
  
      const data = await sendAIChatMessage({
        artworkId,
        message: trimmedQuestion,
        history,
      });
  
      setMessages((prev) => [
        ...prev,
        {
          id: crypto.randomUUID(),
          role: "assistant",
          content: data.reply,
          createdAt: Date.now(),
          status: "sent",
        },
      ]);
  
      setInput("");
    } catch (err) {
      console.error("AI Chat request failed:", err);
  
      setMessages((prev) =>
        prev.map((message) =>
          message.id === messageId
            ? { ...message, status: "failed" }
            : message
        )
      );
  
      setError("Message failed to send. Please retry.");
    } finally {
      setLoading(false);
    }
  }

  function handleSubmit(event: FormEvent<HTMLFormElement>) {
    event.preventDefault();
    void sendMessage(input);
  }

  function clearConversation() {
    if (loading) return;
  
    setMessages([]);
    setInput("");
  }

  return (
    <section className="ai-chat">
      <div className="ai-chat-header">
  <div>
    <p className="ai-chat-eyebrow">YOUR PERSONAL ART GUIDE</p>
    <h2>MuseFlow AI Curator</h2>
  </div>

  <div className="ai-chat-header-actions">
    <span className="ai-chat-status">
    BACKEND DEMO
    </span>

    <button
      type="button"
      className="ai-chat-clear"
      onClick={clearConversation}
      disabled={loading || messages.length === 0}
    >
      Clear conversation
    </button>
  </div>
</div>

      <p className="ai-chat-description">
        Discover the stories, techniques, and history behind this artwork.
      </p>

      <div className="ai-chat-suggestions">
  <p className="ai-chat-suggestions-title">
    SUGGESTED QUESTIONS
  </p>

  <div className="ai-chat-suggestions-list">
    {SUGGESTED_QUESTIONS.map((question) => (
      <button
        key={question}
        type="button"
        disabled={loading}
        onClick={() => void sendMessage(question)}
        className="ai-chat-suggestion"
      >
        {question}
        <span aria-hidden="true">↗</span>
      </button>
    ))}
  </div>
</div>

      <div className="ai-chat-messages" aria-live="polite">
        {messages.length === 0 && (
          <div className="ai-chat-empty">
            <span className="ai-chat-empty-icon">✧</span>
            <p>Curious about this artwork?</p>
            <span>Ask your first question to begin exploring.</span>
          </div>
        )}

{messages.map((message) => (
  <div
    key={message.id}
    className={`ai-chat-message ai-chat-message-${message.role}`}
  >
    <span className="ai-chat-message-label">
      {message.role === "user" ? "YOU" : "AI CURATOR"}
    </span>

    <p>{message.content}</p>

    {message.status === "failed" && (
      <div className="ai-chat-retry">
        <span>Message failed</span>

        <button
          type="button"
          disabled={loading}
          onClick={() =>
            void sendMessage(message.content, message.id)
          }
        >
          ↻ Retry
        </button>
      </div>
    )}
  </div>
))}

{loading && (
  <div
    className="ai-chat-typing"
    role="status"
    aria-label="Curator is responding"
  >
    <span />
    <span />
    <span />
  </div>
)}
{error && (
  <p className="ai-chat-error" role="alert">
    {error}
  </p>
)}
<div ref={messagesEndRef} />
      </div>

      <form className="ai-chat-form" onSubmit={handleSubmit}>
        <input
          type="text"
          value={input}
          onChange={(event) => setInput(event.target.value)}
          placeholder="Ask about this artwork..."
          aria-label="Ask the AI Curator a question"
          disabled={loading}
        />

        <button type="submit" disabled={loading || !input.trim()}>
          Send →
        </button>
      </form>

      <p className="ai-chat-disclaimer">
  Backend demo mode: Messages are processed by MuseFlow AI Service,
  but responses are not generated by an AI model.
</p>
    </section>

  );

  
}