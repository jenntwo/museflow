export interface Artwork {
    id: string;
    exhibitionId: string;
    title: string;
    artist: string;
    yearCreated: number | null;
    medium: string | null;
    description: string | null;
    imageUrl: string | null;
  }