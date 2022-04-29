import { render } from "../test-utils";
import NoResultsFound from "../components/NoResultsFound";

describe("<NoResultsFound>", () => {
  test("renders default options when no props passed", () => {
    const { getByRole } = render(<NoResultsFound />);

    const component = getByRole("heading");
    expect(component).toHaveTextContent("No results found");

    const link = getByRole("link");
    expect(link).toHaveAttribute("href", expect.stringMatching("/"));
  });

  test("renders correctly with props", () => {
    const { getByRole, getByText } = render(
      <NoResultsFound
        header="Test"
        message="test message"
        linkTo="/test-link"
      />
    );

    const component = getByRole("heading");
    expect(component).toHaveTextContent("Test");

    const link = getByRole("link");
    expect(link).toHaveAttribute("href", expect.stringMatching("/test-link"));

    const message = getByText("test message");
    expect(message).toBeInTheDocument();
  });
});
