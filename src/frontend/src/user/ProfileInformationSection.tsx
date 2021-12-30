interface Props {
  children: JSX.Element | JSX.Element[];
  heading: string;
  description: string;
}

const ProfileInformationSection = ({
  children,
  heading,
  description,
}: Props) => {
  return (
    <div className="mt-10 divide-y divide-gray-200">
      <div className="space-y-1">
        <h3 className="text-lg leading-6 font-medium text-gray-900">
          {heading}
        </h3>
        <p className="max-w-2xl text-sm text-gray-500">{description}</p>
      </div>
      <div className="mt-6">
        <dl className="divide-y divide-gray-200">{children}</dl>
      </div>
    </div>
  );
};

export default ProfileInformationSection;
