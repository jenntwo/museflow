import type { Exhibition } from "../types/exhibition";

const API_BASE_URL = "/api/v1";

// 获取某个 Museum 的所有 Exhibitions
export async function getExhibitionsByMuseum(
  museumId: string
): Promise<Exhibition[]> {
  const response = await fetch(
    `${API_BASE_URL}/museums/${museumId}/exhibitions`
  );

  if (!response.ok) {
    throw new Error(
      `Failed to fetch exhibitions: ${response.status}`
    );
  }

  return response.json() as Promise<Exhibition[]>;
}

// 获取单个 Exhibition 的详细信息
export async function getExhibitionById(
  id: string
): Promise<Exhibition> {
  const response = await fetch(
    `${API_BASE_URL}/exhibitions/${id}`
  );

  if (!response.ok) {
    throw new Error(
      `Failed to fetch exhibition: ${response.status}`
    );
  }

  return response.json() as Promise<Exhibition>;
}