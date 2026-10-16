import { useEffect, useState } from "react";
import "./Navbar.css";

export default function Navbar() {
  const [scrolled, setScrolled] = useState(false);
  const [menuOpen, setMenuOpen] = useState(false);

  useEffect(() => {
    const handleScroll = () => {
      setScrolled(window.scrollY > 40);
    };

    handleScroll();

    window.addEventListener("scroll", handleScroll);

    return () => {
      window.removeEventListener("scroll", handleScroll);
    };
  }, []);

  return (
    <header className={`navbar ${scrolled ? "navbar-scrolled" : ""}`}>
      <nav className="navbar-container" aria-label="Main navigation">
        <a href="/" className="navbar-logo">
          MUSEFLOW<span>.</span>
        </a>

        <button
          className="navbar-menu-toggle"
          type="button"
          aria-label={menuOpen ? "Close menu" : "Open menu"}
          aria-expanded={menuOpen}
          aria-controls="navbar-links"
          onClick={() => setMenuOpen(!menuOpen)}
        >
          {menuOpen ? "✕" : "☰"}
        </button>

        <div
          id="navbar-links"
          className={`navbar-links ${menuOpen ? "navbar-links-open" : ""}`}
        >
          <a
            href="#museums"
            className="navbar-link navbar-link-active"
            onClick={() => setMenuOpen(false)}
          >
            Explore
          </a>

          <a
            href="#collections"
            className="navbar-link"
            onClick={() => setMenuOpen(false)}
          >
            Collections
          </a>

          <a
            href="#about"
            className="navbar-link"
            onClick={() => setMenuOpen(false)}
          >
            About
          </a>
        </div>
      </nav>
    </header>
  );
}