import { useAppSelector } from "../hooks/redux-hooks";
import { selectUser } from "../state/userSlice";
import DeleteUserAccountForm from "./DeleteUserAccountForm";
import ProfileInformationItem from "./ProfileInformationItem";
import ProfileInformationSection from "./ProfileInformationSection";

const Profile = () => {
  const user = useAppSelector(selectUser);

  return (
    <div className="relative h-screen bg-white overflow-hidden flex">
      {/* Content area */}
      <div className="flex-1 flex flex-col">
        <main className="flex-1 overflow-y-auto focus:outline-none">
          <div className="relative max-w-4xl mx-auto md:px-8 xl:px-0">
            <div className="pt-10 pb-16">
              <div className="px-4 sm:px-6 md:px-0">
                <h1 className="text-3xl font-extrabold text-gray-900">
                  Account
                </h1>
              </div>
              <div className="px-4 sm:px-6 md:px-0">
                <div className="py-6">
                  <ProfileInformationSection
                    heading="Personal Information"
                    description="This information will be displayed publicly so be
                        careful what you share."
                  >
                    <ProfileInformationItem id="Name" info={user?.username} />
                    <ProfileInformationItem id="Email" info={user?.email} />
                    <ProfileInformationItem id="Password" />
                  </ProfileInformationSection>

                  <ProfileInformationSection
                    heading="Address"
                    description="Hopefully we do not need to explain this."
                  >
                    <ProfileInformationItem
                      id="Line1"
                      info={user?.address?.line1}
                    />
                    <ProfileInformationItem
                      id="Line2"
                      info={user?.address?.line2}
                    />
                    <ProfileInformationItem
                      id="Line3"
                      info={user?.address?.line3}
                    />
                    <ProfileInformationItem
                      id="City"
                      info={user?.address?.city}
                    />
                    <ProfileInformationItem
                      id="Province/State"
                      info={user?.address?.province}
                    />
                    <ProfileInformationItem
                      id="Country"
                      info={user?.address?.country}
                    />
                    <ProfileInformationItem
                      id="Zip Code"
                      info={user?.address?.zipCode}
                    />
                  </ProfileInformationSection>

                  <DeleteUserAccountForm />
                </div>
              </div>
            </div>
          </div>
        </main>
      </div>
    </div>
  );
};

export default Profile;
