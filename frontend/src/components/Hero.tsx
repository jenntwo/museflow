import "./Hero.css";

export default function Hero() {
  return (
    <section className="hero" aria-labelledby="hero-title">
      <div className="hero-background" />

      <div className="hero-overlay" />

      <div className="hero-content">
        <p className="hero-eyebrow">
          MUSEFLOW / THE ART OF DISCOVERY
        </p>

        <h1 id="hero-title" className="hero-title">
          Step into the
          <br />
          <em>extraordinary.</em>
        </h1>

        <p className="hero-description">
          Every artwork has a story.
          <br />
          Discover yours.
        </p>

        <a href="#museums" className="hero-button">
          EXPLORE MUSEUMS
          <span aria-hidden="true">↗</span>
        </a>
      </div>

      <div className="hero-scroll-indicator" aria-hidden="true">
        <span>SCROLL TO EXPLORE</span>
        <span>↓</span>
      </div>
    </section>
  );
}

