import styles from "./Carousel.module.css";
import { CarouselImageContainer } from "./CarouselImageContainer";
import { Button } from "./Button";

import pic1 from "../assets/pic1.jpg";
// import pic2 from "../assets/pic2.jpg";
// import pic3 from "../assets/pic3.jpg";
// import pic4 from "../assets/pic4.jpg";

export const Carousel = () => {
  return (
    <div className={styles.carouselContainer}>
      <img src={pic1} alt="first fashion." className={styles.image} />
      <div className={styles.buttonContainer}>
        <Button
          text="Lorem Ipsum"
          onClickHandler={() => console.log("button clicked")}
        />
      </div>
      {/* <img src={pic2} alt="first fashion." /> */}
      {/* <img src={pic3} alt="first fashion." /> */}
      {/* <img src={pic4} alt="first fashion." /> */}
      <CarouselImageContainer />
    </div>
  );
};
