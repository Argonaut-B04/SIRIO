import React from "react";
import SirioButton from "../SirioButton";
import classes from "./ActionButton.module.css";
import TriggerButton from "./TriggerButton";
import SirioModal from "./SirioModal";

/**
 * Komponen untuk Button yang membuka Popup berupa Konfirmasi
 * 
 * Props yang tersedia:
 * - Seluruh Props SirioButton
 * - modalTitle         : String, judul modal
 * - onConfirm          : Function, dijalankan ketika tombol konfirmasi ditekan
 * - customConfirmText  : String, Opsional untuk mengganti kata "konfirmasi" pada tombol konfirmasi
 */
export default class SirioConfirmButton extends React.Component {

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
        const { onConfirm, modalTitle, customConfirmText, modalDesc } = this.props;
        const { modalButton } = classes;

        return (
            <>
                <TriggerButton {...this.props} handleShow={handleShow} />

                <SirioModal
                    image={<img src={process.env.PUBLIC_URL + "/modal-checklist.png"} height="200px" className="ml-4" alt="checklist" />}
                    modalTitle={modalTitle}
                    modalDesc={modalDesc}
                    handleClose={handleClose}
                    show={show}
                    footerButton={
                        <>
                            <SirioButton
                                purple
                                recommended={!this.props.confirmDisable}
                                text={this.props.confirmDisable}
                                disable={this.props.confirmDisable}
                                circular
                                onClick={() => {
                                    if (onConfirm) {
                                        onConfirm()
                                    }
                                    handleClose()
                                }}
                                classes={modalButton}
                            >
                                {customConfirmText ? customConfirmText : "Konfirmasi"}
                            </SirioButton>
                            <SirioButton
                                purple
                                circular
                                onClick={handleClose}
                                classes={modalButton}
                            >
                                Batal
                            </SirioButton>
                        </>
                    }
                />
            </>
        );
    }
}