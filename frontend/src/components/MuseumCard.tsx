import { Link } from "react-router-dom";
import type { Museum } from "../types/museum";
import "./MuseumCard.css";

interface MuseumCardProps {
  museum: Museum;
  index: number;
}

export default function MuseumCard({
  museum,
  index,
}: MuseumCardProps) {
  const fallbackImage =
    "https://images.unsplash.com/photo-1564399579883-451a5d44ec08?auto=format&fit=crop&w=1200&q=85";

  return (
    <article className="museum-card">
      <Link
        to={`/museums/${museum.id}`}
        className="museum-card-link"
      >
        <div className="museum-card-image-wrapper">
          <img
            className="museum-card-image"
            src={museum.imageUrl || fallbackImage}
            alt={museum.name}
          />

          <span className="museum-card-number">
            {String(index + 1).padStart(2, "0")}
          </span>

          <div className="museum-card-explore">
            EXPLORE
            <span>↗</span>
          </div>
        </div>

        <div className="museum-card-content">
          <p className="museum-card-location">
            {museum.city} · {museum.country}
          </p>

          <h3 className="museum-card-title">
            {museum.name}
          </h3>

          {museum.description && (
            <p className="museum-card-description">
              {museum.description}
            </p>
          )}
        </div>
      </Link>
    </article>
  );
}