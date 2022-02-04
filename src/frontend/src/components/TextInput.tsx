import { ChangeEvent } from "react";

interface TextInputProps {
  type?: string;
  name: string;
  id: string;
  value: string;
  autoComplete: string;
  onChangeHandler: (event: ChangeEvent<HTMLInputElement>) => void;
  onFocusHandler?: () => void;
  required?: boolean;
  activeError?: boolean;
  errorMessage?: string;
}

export const TextInput = ({
  type,
  name,
  id,
  value,
  autoComplete,
  onChangeHandler,
  onFocusHandler,
  required,
  activeError,
  errorMessage,
}: TextInputProps) => {
  const errorSettings =
    "pr-10 border-red-300 text-red-900 focus:ring-red-500 focus:border-red-500";
  const normalSettings =
    "px-3 border-gray-300 focus:ring-indigo-500 focus:border-indigo-500";

  let requireInput = true;
  if (!required) {
    requireInput = false;
  }

  return (
    <div>
      <label
        htmlFor={name}
        className="block sm:text-sm md:text-lg xl:text-xl font-medium text-gray-700"
      >
        {name.charAt(0).toUpperCase() + name.slice(1)}
      </label>
      <div className="mt-1 relative rounded-md shadow-sm">
        <input
          type={type ? type : "text"}
          name={name}
          id={id}
          className={`block w-full py-2 sm:text-sm md:text-lg xl:text-xl border rounded-md shadow-sm placeholder-gray-400 focus:outline-none ${
            activeError ? errorSettings : normalSettings
          }`}
          value={value}
          onChange={onChangeHandler}
          autoComplete={autoComplete}
          required={requireInput}
          onFocus={onFocusHandler}
        />
        <div
          className={`${
            activeError ? "" : "hidden"
          } absolute inset-y-0 right-0 pr-3 flex items-center pointer-events-none`}
        >
          <svg
            className="h-5 w-5 text-red-500"
            xmlns="http://www.w3.org/2000/svg"
            viewBox="0 0 20 20"
            fill="currentColor"
            aria-hidden="true"
          >
            <path
              fillRule="evenodd"
              d="M18 10a8 8 0 11-16 0 8 8 0 0116 0zm-7 4a1 1 0 11-2 0 1 1 0 012 0zm-1-9a1 1 0 00-1 1v4a1 1 0 102 0V6a1 1 0 00-1-1z"
              clipRule="evenodd"
            />
          </svg>
        </div>
      </div>
      <p className={`${activeError ? "" : "hidden"} mt-1 text-sm text-red-600`}>
        {errorMessage}
      </p>
    </div>
  );
};
