import { GithubOutlined } from '@ant-design/icons';
import { DefaultFooter } from '@ant-design/pro-components';
import {ACWING_LINK, GIT_LINK, PLANET_LINK} from "@/constant";
const Footer: React.FC = () => {
  const defaultMessage = 'white UserCenter';
  const currentYear = new Date().getFullYear();
  return (
    <DefaultFooter
      copyright={`${currentYear} ${defaultMessage}`}
      links={[
        {
          key: 'AcWing',
          title: 'AcWing',
          href: ACWING_LINK,
          blankTarget: true,
        },
        {
          key: 'github',
          title: <GithubOutlined />,
          href: GIT_LINK,
          blankTarget: true,
        },
        {
          key: 'planet',
          title: '星球',
          href: PLANET_LINK,
          blankTarget: true,
        },
      ]}
    />
  );
};
export default Footer;
