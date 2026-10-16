export interface AIConversationMessage {
    role: "user" | "assistant";
    content: string;
  }
  
  export interface AIChatRequest {
    artworkId: string;
    message: string;
    history?: AIConversationMessage[];
  }
  
  export interface AIChatResponse {
    artworkId: string;
    artworkContext: {
      title: string;
      artist: string;
      yearCreated: number | null;
      medium: string | null;
      description: string | null;
      demoMode?: boolean;
      conversationLength?: number;
    };
    reply: string;
  }
  
  export async function sendAIChatMessage(
    request: AIChatRequest
  ): Promise<AIChatResponse> {
    const response = await fetch("/api/v1/ai/chat", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(request),
    });
  
    if (!response.ok) {
      throw new Error(
        `AI Chat request failed: ${response.status}`
      );
    }
  
    const data: AIChatResponse = await response.json();
  
    return data;
  }