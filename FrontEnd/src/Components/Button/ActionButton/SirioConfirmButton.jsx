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
 * - closeOnConfirm     : Boolean, tutup modal ketika confirm ditekan
 * - customConfirmText  : String, Opsional untuk mengganti kata "konfirmasi" pada tombol konfirmasi
 */
export default class SirioWarningButton extends React.Component {

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
        const { onConfirm, closeOnConfirm, modalTitle, customConfirmText } = this.props;
        const { modalButton } = classes;
        var onConfirmFunction;

        if (onConfirm) {
            if (closeOnConfirm) {
                onConfirmFunction = () => {
                    handleClose();
                    onConfirm();
                }
            } else {
                onConfirmFunction = onConfirm;
            }
        } else {
            onConfirmFunction = handleClose;
        }
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

                        <div className="d-flex justify-content-center align-items-center w-100">
                            <SirioButton
                                purple
                                recommended
                                circular
                                onClick={onConfirmFunction}
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