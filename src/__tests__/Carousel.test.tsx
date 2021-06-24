import { render } from "@testing-library/react";

import { Carousel } from "../components/Carousel";

describe("<Carousel>", () => {
  test("renders", () => {
    const { getByRole, getAllByRole } = render(<Carousel />);

    const image = getByRole("img");
    const buttons = getAllByRole("button");

    expect(image).toBeInTheDocument();
    expect(buttons).toHaveLength(2);
  });
});
