import styles from "./CarouselImageContainer.module.css";

interface Props {
  picture: any;
  altText: string;
}

export const CarouselImageContainer = ({ picture, altText }: Props) => {
  return <img src={picture} alt={altText} className={styles.image} />;
};
