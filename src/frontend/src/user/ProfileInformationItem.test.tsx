import userEvent from "@testing-library/user-event";
import { render } from "../test-utils/test-utils";
import ProfileInformationItem from "./ProfileInformationItem";

describe("<ProfileInformationItem>", () => {
  test("initially renders info from supplied props", () => {
    const { getByRole } = render(
      <ProfileInformationItem id="Username" info="Test Username" />
    );

    const descTerm = getByRole("term");
    expect(descTerm).toHaveTextContent("Username");

    const descDetails = getByRole("definition");
    expect(descDetails).toHaveTextContent("Test Username");

    const updateButton = getByRole("button");
    expect(updateButton).toHaveTextContent("Update");
  });

  test("shows dots when info item is password", () => {
    const { getByRole } = render(<ProfileInformationItem id="Password" />);

    const descDetails = getByRole("definition");
    expect(descDetails).toHaveTextContent("••••••••");
  });

  test("reveals update form when update button is clicked", async () => {
    const user = userEvent.setup();
    const { getByRole, getByLabelText } = render(
      <ProfileInformationItem id="Username" info="Test Username" />
    );

    const updateButton = getByRole("button");

    await user.click(updateButton);

    const updateInput = getByLabelText("Username");
    expect(updateInput).toBeInTheDocument();
  });

  test("hides update form when cancel button is clicked", async () => {
    const user = userEvent.setup();
    const { getByRole, getAllByRole } = render(
      <ProfileInformationItem id="Username" info="Test Username" />
    );

    const updateButton = getByRole("button");

    await user.click(updateButton);

    const buttons = getAllByRole("button");
    expect(buttons[0]).toHaveTextContent("Cancel");

    await user.click(buttons[0]);

    expect(buttons[0]).not.toBeInTheDocument();
  });
});
