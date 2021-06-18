import styles from "./iconButton.module.css";

interface Props {
  icon: JSX.Element;
  onClickHandler: () => void;
}

export const IconButton = ({ icon, onClickHandler }: Props) => {
  return (
    <button type="button" onClick={onClickHandler} className={styles.button}>
      {icon}
    </button>
  );
};
