import React from "react";
import Modal from "react-bootstrap/Modal";
import SirioButton from "../SirioButton";
import classes from "./ActionButton.module.css";

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
        const { onConfirm, modalTitle, customConfirmText } = this.props;
        const { modalButton } = classes;

        return (
            <>
                <SirioButton
                    {...this.props}
                    onClick={handleShow}
                >
                    {this.props.children}
                </SirioButton>

                <Modal
                    size="md"
                    show={show}
                    onHide={handleClose}
                    centered>
                    <Modal.Body className="d-flex justify-content-center align-items-center flex-column py-5">
                        <div className="text-center p-3 w-75">
                            <h3 >{modalTitle}</h3>
                        </div>

                        <div className="text-center px-0 mx-0 w-75">
                            <div>{this.props.modalDesc}</div>
                        </div>

                        <div className="d-flex justify-content-center align-items-center w-100">
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
                        </div>
                    </Modal.Body>
                </Modal>
            </>
        );
    }
}