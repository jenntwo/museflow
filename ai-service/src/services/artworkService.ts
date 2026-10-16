import type { Artwork } from "../types/artwork.js";

const BACKEND_URL =
  process.env.BACKEND_URL || "http://localhost:8080";

export class ArtworkNotFoundError extends Error {
  constructor() {
    super("Artwork not found.");
    this.name = "ArtworkNotFoundError";
  }
}

export async function getArtworkById(
  artworkId: string
): Promise<Artwork> {
  const response = await fetch(
    `${BACKEND_URL}/api/v1/artworks/${encodeURIComponent(artworkId)}`,
    {
      signal: AbortSignal.timeout(5000),
    }
  );

  if (response.status === 404) {
    throw new ArtworkNotFoundError();
  }

  if (!response.ok) {
    throw new Error(
      `Spring Boot API error: ${response.status}`
    );
  }

  return (await response.json()) as Artwork;
}