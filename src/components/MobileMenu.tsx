import styles from "./mobileMenu.module.css";
import { Menu, X } from "react-feather";
import { IconButton } from "./IconButton";
import { useState } from "react";

export const MobileMenu = () => {
  const [openMenu, setOpenMenu] = useState(false);

  return (
    <div className={styles.mobileMenuContainer}>
      <IconButton
        icon={<Menu size={24} />}
        onClickHandler={() => setOpenMenu(true)}
      />
      <div className={`${openMenu ? styles.mobileMenu : styles.closed}`}>
        <div className={styles.mobileMenuHeader}>
          <IconButton
            icon={<X size={24} color="black" />}
            onClickHandler={() => setOpenMenu(false)}
          />
        </div>
        <nav className={styles.navLinks}>
          <ul>
            <li>
              <a href="#">Link</a>
            </li>
            <li>
              <a href="#">Link</a>
            </li>
            <li>
              <a href="#">Link</a>
            </li>
            <li>
              <a href="#">Link</a>
            </li>
          </ul>
        </nav>
        <div className={styles.cartContainer}>
          <p>PROFILE</p>
          <p>CART</p>
        </div>
      </div>
    </div>
  );
};
