import type { Museum } from "../types/museum";

const API_BASE_URL = "/api/v1";

export async function getMuseums(): Promise<Museum[]> {
  const response = await fetch(`${API_BASE_URL}/museums`);

  if (!response.ok) {
    throw new Error(
      `Failed to fetch museums: ${response.status}`
    );
  }

  return response.json() as Promise<Museum[]>;
}

export async function getMuseumById(
  id: string
): Promise<Museum> {
  const response = await fetch(`${API_BASE_URL}/museums/${id}`);

  if (!response.ok) {
    throw new Error(
      `Failed to fetch museum: ${response.status}`
    );
  }

  return response.json() as Promise<Museum>;
}