import { render } from "../test-utils/test-utils";
import UpdateInfoForm from "./UpdateInfoForm";

const setShowUpdateForm = jest.fn();

describe("<UpdateInfoForm>", () => {
  test("renders correctly without reauth required", () => {
    const { getByLabelText, getAllByRole } = render(
      <UpdateInfoForm
        id="Username"
        currentInfo="Test Username"
        setShowUpdateForm={setShowUpdateForm}
      />
    );

    const input = getByLabelText("Username");
    expect(input).toBeInTheDocument();

    const buttons = getAllByRole("button");
    expect(buttons[0]).toHaveTextContent("Cancel");
    expect(buttons[1]).toHaveTextContent("Update");
  });

  test("renders correctly with reauth required", () => {
    const { getByLabelText, getAllByRole } = render(
      <UpdateInfoForm
        id="Username"
        currentInfo="Test Username"
        reauthRequired={true}
        setShowUpdateForm={setShowUpdateForm}
      />
    );

    const input = getByLabelText("Username");
    expect(input).toBeInTheDocument();

    const currentPasswordInput = getByLabelText("Current Password");
    expect(currentPasswordInput).toBeInTheDocument();

    const buttons = getAllByRole("button");
    expect(buttons[0]).toHaveTextContent("Cancel");
    expect(buttons[1]).toHaveTextContent("Update");
  });
});
