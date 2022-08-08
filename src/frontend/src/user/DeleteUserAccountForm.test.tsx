import userEvent from "@testing-library/user-event";
import { render } from "../test-utils/test-utils";
import DeleteUserAccountForm from "./DeleteUserAccountForm";

describe("<DeleteUserAccountForm>", () => {
  test("initially renders message and button", () => {
    const { getByRole, getByText } = render(<DeleteUserAccountForm />);

    const heading = getByRole("heading");
    expect(heading).toHaveTextContent("Delete Account");

    const message = getByText(
      "This action will permanently delete this account and all associated project Ideas."
    );
    expect(message).toBeInTheDocument();

    const button = getByRole("button");
    expect(button).toBeInTheDocument();
    expect(button).toHaveTextContent("Delete Account");
  });

  test("reveals password input and form buttons when Delete Account button is clicked", async () => {
    const user = userEvent.setup();
    const { getByRole, getByDisplayValue, getAllByRole } = render(
      <DeleteUserAccountForm />
    );

    const button = getByRole("button");
    await user.click(button);

    const passwordInput = getByDisplayValue("");
    expect(passwordInput).toBeInTheDocument();

    const formButtons = getAllByRole("button");
    expect(formButtons[0]).toHaveTextContent("Cancel");
    expect(formButtons[0]).toHaveAttribute("type", "button");

    expect(formButtons[1]).toHaveTextContent("Yes, delete my account");
    expect(formButtons[1]).toHaveAttribute("type", "submit");
  });
});
