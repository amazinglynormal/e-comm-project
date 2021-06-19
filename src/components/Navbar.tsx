import { Menu } from "react-feather";
import { IconButton } from "./IconButton";
import styles from "./navbar.module.css";

export const Navbar = () => {
  return (
    <div className={styles.navbar}>
      <div>
        <p>BRAND</p>
      </div>
      <nav className={styles.desktopNavLinks}>
        <a href="#" className={styles.navLink}>
          Link
        </a>
        <a href="#" className={styles.navLink}>
          Link
        </a>
        <a href="#" className={styles.navLink}>
          Link
        </a>
        <a href="#" className={styles.navLink}>
          Link
        </a>
      </nav>
      <div>
        <div className={styles.desktopRight}>
          <p>PROFILE</p>
          <p>CART</p>
        </div>
        <div className={styles.mobileMenu}>
          <IconButton
            icon={<Menu size={24} />}
            onClickHandler={() => console.log("clicked")}
          />
        </div>
      </div>
    </div>
  );
};
