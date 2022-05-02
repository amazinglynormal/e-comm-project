import { render } from "../test-utils/test-utils";
import ForgotPassword from "./ForgotPassword";

describe("<ForgotPassword>", () => {
  test("renders correctly", () => {
    const { getByRole, getByLabelText } = render(<ForgotPassword />);

    const emailInput = getByLabelText("Email address");
    expect(emailInput).toBeInTheDocument();

    const submitButton = getByRole("button");
    expect(submitButton).toHaveTextContent("Request password reset");
    expect(submitButton).toHaveAttribute("type", "submit");

    const link = getByRole("link");
    expect(link).toHaveAttribute("href", "/");
    expect(link).toHaveTextContent("Return to home page -->");
  });
});
