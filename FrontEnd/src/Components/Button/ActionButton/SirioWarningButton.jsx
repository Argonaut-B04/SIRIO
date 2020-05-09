import React from "react";
import Modal from "react-bootstrap/Modal";
import SirioButton from "../SirioButton";
import "bootstrap/dist/css/bootstrap.min.css";
import classes from "./ActionButton.module.css";

/**
 * Komponen untuk Button yang membuka Popup berupa Warning
 * 
 * Props yang tersedia:
 * - Seluruh Props SirioButton
 * - modalTitle         : String, judul modal
 * - modalDesc          : String, deskripsi yang akan ditampilkan dalam modal
 * - onConfirm          : Function, dijalankan ketika tombol lanjutkan ditekan
 * - customConfirmText  : String, Opsional untuk mengganti kata "lanjutkan" pada tombol lanjutkan
 * - customCancelText   : String, Opsional untuk mengganti kata "Kembali" pada tombol pembatalan
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
        const { handleClose, handleShow, state, props } = this;
        const { show } = state;
        const { modalTitle, modalDesc, onConfirm, customConfirmText, customCancelText } = props;
        const { modalButton, modalDescWrapper } = classes;

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
                        <img src={process.env.PUBLIC_URL + '/trashbin.svg'} width="200px" alt="trashbin" />

                        {modalTitle &&
                            <div className="text-center p-3 w-75">
                                <h3 >{modalTitle}</h3>
                            </div>
                        }

                        {modalDesc &&
                            <div className={modalDescWrapper}>
                                <div>{modalDesc}</div>
                            </div>
                        }

                        <div className="d-flex justify-content-center align-items-center w-100">
                            <SirioButton
                                purple
                                recommended
                                circular
                                onClick={() => {
                                    if (onConfirm) {
                                        onConfirm()
                                    }
                                    handleClose()
                                }}
                                classes={modalButton}
                            >
                                {customConfirmText ? customConfirmText : "Lanjutkan"}
                            </SirioButton>
                            
                            <SirioButton
                                purple
                                circular
                                onClick={handleClose}
                                classes={modalButton}
                            >
                                {customCancelText ? customCancelText : "Kembali"}
                            </SirioButton>
                        </div>
                    </Modal.Body>
                </Modal>
            </>
        );
    }
}