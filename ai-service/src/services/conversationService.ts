import type { Artwork } from "../types/artwork.js";


export interface ConversationMessage {
  role: "user" | "assistant";
  content: string;
}

export function buildConversation(
  artwork: Artwork,
  history: ConversationMessage[],
  question: string
): ConversationMessage[] {
  const artworkContext = [
    `Artwork: ${artwork.title}`,
    `Artist: ${artwork.artist}`,
    `Year: ${artwork.yearCreated ?? "Unknown"}`,
    `Medium: ${artwork.medium ?? "Unknown"}`,
    `Description: ${artwork.description ?? "Not available"}`,
  ].join("\n");

  return [
    {
      role: "user",
      content:
        `Artwork reference information:\n${artworkContext}`,
    },
    ...history,
    {
      role: "user",
      content: question,
    },
  ];
}