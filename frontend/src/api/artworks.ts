import type { Artwork } from "../types/artwork";

const API_BASE_URL = "/api/v1";

export async function getArtworksByExhibition(
  exhibitionId: string
): Promise<Artwork[]> {
  const response = await fetch(
    `${API_BASE_URL}/exhibitions/${exhibitionId}/artworks`
  );

  if (!response.ok) {
    throw new Error(
      `Failed to fetch artworks: ${response.status}`
    );
  }

  return response.json() as Promise<Artwork[]>;
}

export async function getArtworkById(
    id: string
  ): Promise<Artwork> {
    const response = await fetch(
      `${API_BASE_URL}/artworks/${id}`
    );
  
    if (!response.ok) {
      throw new Error(
        `Failed to fetch artwork: ${response.status}`
      );
    }
  
    return response.json() as Promise<Artwork>;
  }