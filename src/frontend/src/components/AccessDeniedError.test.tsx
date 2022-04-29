import { render } from "../test-utils/test-utils";
import AccessDeniedError from "../components/AccessDeniedError";

describe("<AccessDeniedError>", () => {
  test("renders correctly with props", () => {
    const { getByText, getByRole } = render(
      <AccessDeniedError message="test message" linkTo="/test-link" />
    );

    const message = getByText("test message");
    expect(message).toBeInTheDocument();

    const link = getByRole("link");
    expect(link).toHaveAttribute("href", expect.stringMatching("/test-link"));
  });
});
