import styles from "./Button.module.css";

interface Props {
  text: string;
  type?: "button" | "submit" | "reset";
  onClickHandler: () => void;
}

export const Button = ({ text, onClickHandler, type = "button" }: Props) => {
  return (
    <button type={type} className={styles.button} onClick={onClickHandler}>
      {text}
    </button>
  );
};
