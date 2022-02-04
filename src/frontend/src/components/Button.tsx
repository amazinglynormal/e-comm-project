interface ButtonProps {
  type: "button" | "submit" | "reset";
  icon?: JSX.Element;
  iconPosition?: "start" | "end";
  variant: "primary" | "secondary" | "tertiary" | "plain" | "danger";
  size: "small" | "medium" | "large" | "fullWidth";
  text: string;
  onClickHandler?: () => void;
}

export const Button = ({
  type,
  icon,
  iconPosition,
  variant,
  size,
  text,
  onClickHandler,
}: ButtonProps) => {
  const sizes = {
    small: "px-2.5 py-1.5 text-xs",
    medium: "px-4 py-2 text-sm",
    large: "px-4 py-2 text-base",
    fullWidth: "py-2 w-full text-base text-center",
  };

  const variants = {
    primary:
      "border-transparent text-white bg-indigo-600 hover:bg-indigo-700 shadow-md ",
    secondary:
      "border-indigo-100 text-indigo-700 bg-indigo-100 hover:bg-indigo-200 shadow-md ",
    tertiary:
      "border-gray-100 text-indigo-700 hover:text-indigo-800 hover:bg-gray-50 shadow-md ",
    plain: "text-gray-500 hover:text-gray-900",
    danger: "border-red-100 text-white bg-red-700 hover:bg-red-800 shadow-md",
  };

  return (
    <button
      type={type}
      className={`${sizes[size]} border ${variants[variant]} group inline-flex items-center font-medium rounded-md focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500`}
      onClick={onClickHandler}
    >
      {iconPosition === "start" && icon}
      <span
        className={` ${iconPosition === "start" && "ml-2"} ${
          iconPosition === "end" && "mr-2"
        } ${size === "fullWidth" && "text-center w-full"}`}
      >
        {text}
      </span>
      {iconPosition === "end" && icon}
    </button>
  );
};
