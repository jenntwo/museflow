import type { Artwork } from "../types/artwork";
import "./ArtworkCard.css";
import { Link } from "react-router-dom";

interface ArtworkCardProps {
  artwork: Artwork;
}

export default function ArtworkCard({ artwork }: ArtworkCardProps) {
  return (
    <article className="artwork-card">
         <Link
    to={`/artworks/${artwork.id}`}
    className="artwork-card-link"
  >
      <div className="artwork-card-image-wrapper">
        {artwork.imageUrl && (
          <img
            src={artwork.imageUrl}
            alt={artwork.title}
            className="artwork-card-image"
            onError={(event) => {
              event.currentTarget.style.display = "none";
            }}
          />
        )}

        <span className="artwork-card-label">MUSEFLOW COLLECTION</span>
      </div>

      <div className="artwork-card-info">
        <p className="artwork-card-artist">{artwork.artist}</p>
        <h3>{artwork.title}</h3>
        <p className="artwork-card-meta">
          {[artwork.yearCreated, artwork.medium]
            .filter((value) => value != null && value !== "")
            .join(" · ")}
        </p>
      </div>
      </Link>

    </article>
  );
}