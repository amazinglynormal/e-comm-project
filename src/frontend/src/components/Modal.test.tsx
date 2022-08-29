import { render } from "../test-utils/test-utils";
import Modal from "./Modal";

describe("<Modal>", () => {
  test("renders correctly", () => {
    const { getByText, getByRole } = render(
      <Modal isOpen={true} setIsOpen={() => false} />
    );

    const modal = getByText(
      "As this is a demo, you will be redirected to the order summary page rather than the Stripe payment page in order to avoid any mishaps with entering card details."
    );

    expect(modal).toBeInTheDocument();

    const modalButton = getByRole("button");

    expect(modalButton).toHaveTextContent("I understand");
  });
});
