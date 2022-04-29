import { render } from "../test-utils";
import { TextInput } from "../components/TextInput";
import userEvent from "@testing-library/user-event";

const changeHandler = jest.fn();

describe("<TextInput>", () => {
  test("renders correctly", () => {
    const { getByLabelText } = render(
      <TextInput
        type="text"
        name="test"
        id="test"
        value={"test value"}
        autoComplete="username"
        onChangeHandler={changeHandler}
      />
    );

    const input = getByLabelText("Test");
    expect(input).toBeInTheDocument();
  });

  test("shows error message passed to it", () => {
    const { getByText } = render(
      <TextInput
        type="text"
        name="test"
        id="test"
        value={"test value"}
        autoComplete="username"
        onChangeHandler={changeHandler}
        activeError={true}
        errorMessage="error message"
      />
    );

    const input = getByText("error message");
    expect(input).toBeInTheDocument();
  });

  test("value changes when user types in input", () => {
    const { getByDisplayValue } = render(
      <TextInput
        type="text"
        name="test"
        id="test"
        value={"test value"}
        autoComplete="username"
        onChangeHandler={changeHandler}
      />
    );

    const input = getByDisplayValue("test value");
    expect(input).toHaveDisplayValue("test value");
    userEvent.type(input, "foo");
    expect(changeHandler).toHaveBeenCalledTimes(3);
  });

  test("focus handler fires when clicked", () => {
    const focusHandler = jest.fn();
    const { getByLabelText } = render(
      <TextInput
        type="text"
        name="test"
        id="test"
        value={"test value"}
        autoComplete="username"
        onChangeHandler={changeHandler}
        onFocusHandler={focusHandler}
      />
    );

    const input = getByLabelText("Test");
    userEvent.click(input);
    expect(focusHandler).toHaveBeenCalledTimes(1);
  });
});
