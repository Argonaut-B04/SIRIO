import React from "react";
import Modal from "react-bootstrap/Modal";
import SirioButton from "../SirioButton";
import "bootstrap/dist/css/bootstrap.min.css";
import classes from "./ActionButton.module.css";
import TriggerButton from "./TriggerButton";
import SirioModal from "./SirioModal";

/**
 * Komponen untuk Button yang membuka Popup berupa Message
 * 
 * Props yang tersedia:
 * - Seluruh Props SirioButton
 * - modalTitle         : String, judul modal
 * - onClick            : Function, dijalankan ketika tombol oke ditekan
 * - customConfirmText  : String, Opsional untuk mengganti kata "oke" pada tombol oke
 */
export default class SirioMessageButton extends React.Component {

    constructor(props, context) {
        super(props, context);

        this.state = {
            show: false,
        };

        this.handleShow = this.handleShow.bind(this);
        this.handleClose = this.handleClose.bind(this);

    }

    componentDidMount() {
        if (this.props.show) {
            this.handleShow();
        }
    }

    handleClose() {
        this.setState({ show: false });
    }

    handleShow() {
        this.setState({ show: true });
    }

    render() {
        const { handleClose, handleShow } = this;
        const { show } = this.state;
        const { modalTitle, onClick, customConfirmText, modalDesc } = this.props;
        const { modalButton } = classes;
        return (
            <>
                <TriggerButton {...this.props} handleShow={this.handleShow} />

                <SirioModal
                    image={<img src={process.env.PUBLIC_URL + "/modal-checklist.png"} height="200px" className="ml-4" alt="checklist" />}
                    modalTitle={modalTitle}
                    modalDesc={modalDesc}
                    show={show}
                    footerButton={
                        <SirioButton
                            purple
                            recommended
                            circular
                            onClick={() => {
                                if (onClick) {
                                    onClick()
                                }
                                handleClose()
                            }}
                            classes={modalButton}
                        >
                            {customConfirmText ? customConfirmText : "oke"}
                        </SirioButton>
                    }
                />
            </>
        );
    }
}