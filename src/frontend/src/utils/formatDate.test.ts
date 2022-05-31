import formatDate from "./formatDate";

describe("formatDate()", () => {
  test("returns correct format", () => {
    const testDate = "2022-05-31T12:32:45.877+00:00";
    const formattedDate = formatDate(testDate);

    expect(formattedDate).toBe("2022-05-31");
  });
});
