import { ArrowLeft, ArrowRight } from "react-feather";
import styles from "./CarouselButton.module.css";

interface Props {
  direction: "forward" | "back";
  onClickHandler: () => void;
}

export const CarouselButton = ({ direction, onClickHandler }: Props) => {
  return (
    <button
      type="button"
      onClick={onClickHandler}
      className={
        direction === "forward" ? styles.buttonRight : styles.buttonLeft
      }
    >
      {direction === "forward" ? <ArrowRight /> : <ArrowLeft />}
    </button>
  );
};
