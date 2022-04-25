import { render } from "../test-utils";
import SignUp from "./SignUp";

describe("<SignUp>", () => {
  test("renders correctly", () => {
    const { getByRole, getByLabelText } = render(<SignUp />);

    const heading = getByRole("heading");
    expect(heading).toHaveTextContent("Create your account");

    const nameLabel = getByLabelText("Name");
    expect(nameLabel).toBeInTheDocument();

    const emailLabel = getByLabelText("Email address");
    expect(emailLabel).toBeInTheDocument();

    const passwordLabel = getByLabelText("Password");
    expect(passwordLabel).toBeInTheDocument();

    const button = getByRole("button");
    expect(button).toHaveTextContent("Create account");
    expect(button).toHaveAttribute("type", "submit");

    const link = getByRole("link");
    expect(link).toHaveAttribute("href", "/login");
  });
});
