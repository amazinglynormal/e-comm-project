import { render } from "../test-utils";
import LogIn from "./LogIn";

describe("<LogIn>", () => {
  test("renders correctly", () => {
    const { getByRole, getByLabelText, getAllByRole } = render(<LogIn />);

    const heading = getByRole("heading");
    expect(heading).toHaveTextContent("Sign in to your account");

    const emailLabel = getByLabelText("Email address");
    expect(emailLabel).toBeInTheDocument();

    const passwordLabel = getByLabelText("Password");
    expect(passwordLabel).toBeInTheDocument();

    const button = getByRole("button");
    expect(button).toHaveTextContent("Sign in");
    expect(button).toHaveAttribute("type", "submit");

    const links = getAllByRole("link");
    expect(links[0]).toHaveAttribute("href", "/forgotpassword");
    expect(links[0]).toHaveTextContent("Forgot your password?");
    expect(links[1]).toHaveAttribute("href", "/signup");
    expect(links[1]).toHaveTextContent("Sign up here.");
  });
});
