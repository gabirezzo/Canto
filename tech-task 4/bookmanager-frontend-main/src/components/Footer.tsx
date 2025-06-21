import { Container } from "reactstrap";
// used for making the prop types of this component
import PropTypes from "prop-types";

interface FooterProps {
  default?: boolean;
  fluid?: boolean;
}

function Footer(props: FooterProps) {
  return (
    <footer className={"footer" + (props.default ? " footer-default" : "")}>
      <Container fluid={props.fluid ? true : false}>
        <div className="copyright">
          &copy; {new Date().getFullYear()}, Designed by{" "}
        </div>
      </Container>
    </footer>
  );
}

Footer.propTypes = {
  default: PropTypes.bool,
  fluid: PropTypes.bool,
};

export default Footer;
