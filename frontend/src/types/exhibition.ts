export interface Exhibition {
    id: string;
    museumId: string;
    title: string;
    description: string | null;
    startDate: string | null;
    endDate: string | null;
    imageUrl: string | null;
  }