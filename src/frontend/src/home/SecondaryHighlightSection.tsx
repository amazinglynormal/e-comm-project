import { Link } from "react-router-dom";

export const SecondaryHighlightSection = () => {
  return (
    <div className="bg-white">
      <div className="max-w-7xl mx-auto py-16 px-4 sm:py-24 sm:px-6 lg:px-8">
        <div className="relative rounded-lg overflow-hidden">
          <div className="absolute inset-0">
            <img
              src="/images/belts.jpg"
              alt="A wall of belts of varying colours"
              className="w-full h-full object-center object-cover"
            />
          </div>
          <div className="relative bg-gray-900 bg-opacity-75 py-32 px-6 sm:py-40 sm:px-12 lg:px-16">
            <div className="relative max-w-3xl mx-auto flex flex-col items-center text-center">
              <h2 className="text-3xl font-extrabold tracking-tight text-white sm:text-4xl">
                <span className="block sm:inline">Level up</span>
                <span className="block sm:inline"> your waist</span>
              </h2>
              <p className="mt-3 text-xl text-white">
                &quot;My belt holds my pants up, but the belt loops hold my belt
                up. I don't really know what's happening down there. Who is the
                real hero?&quot; - Mitch Hedberg
              </p>
              <Link
                to="/profile"
                className="mt-8 w-full block bg-white border border-transparent rounded-md py-3 px-8 text-base font-medium text-gray-900 hover:bg-gray-100 sm:w-auto"
              >
                Shop Belts
              </Link>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};
