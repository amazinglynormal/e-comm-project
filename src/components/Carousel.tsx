import { useState } from "react";
import styles from "./Carousel.module.css";
import { CarouselImageContainer } from "./CarouselImageContainer";
import { CarouselButton } from "./CarouselButton";
import { Button } from "./Button";

import pic1 from "../assets/pic1.jpg";
import pic2 from "../assets/pic2.jpg";
import pic3 from "../assets/pic3.jpg";
import pic4 from "../assets/pic4.jpg";

interface CarouselPictures {
  picture: string;
  altText: string;
  linkTo: string;
}

const defaultPics = [
  { picture: pic1, altText: "current image in use" },
  { picture: pic2, altText: "current image in use" },
  { picture: pic3, altText: "current image in use" },
  { picture: pic4, altText: "current image in use" },
];

export const Carousel = () => {
  const [current, setCurrent] = useState(0);
  const [pics, setPics] = useState(defaultPics);

  const onBackClick = () => {
    if (current === 0) {
      setCurrent(pics.length - 1);
    } else {
      setCurrent((curr) => curr - 1);
    }
  };

  const onForwardClick = () => {
    if (current === pics.length - 1) {
      setCurrent(0);
    } else {
      setCurrent((curr) => curr + 1);
    }
  };

  return (
    <div className={styles.carouselContainer}>
      <CarouselImageContainer
        picture={pics[current].picture}
        altText={pics[current].altText}
      />
      <CarouselButton direction="back" onClickHandler={onBackClick} />
      <CarouselButton direction="forward" onClickHandler={onForwardClick} />
    </div>
  );
};
