import React from "react";
import Modal from "react-bootstrap/Modal";
import SirioButton from "../SirioButton";
import "bootstrap/dist/css/bootstrap.min.css";
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
        var onConfirm;

        if (this.props.onConfirm) {
            if (this.props.closeOnConfirm) {
                onConfirm = () => {
                    this.handleClose();
                    this.props.onConfirm();
                }
            } else {
                onConfirm = this.props.onConfirm;
            }
        } else {
            onConfirm = this.handleClose;
        }
        return (
            <>
                <SirioButton
                    {...this.props}
                    onClick={this.handleShow}
                >
                    {this.props.children}
                </SirioButton>

                <Modal
                    size="md"
                    show={this.state.show}
                    onHide={this.handleClose}
                    centered>
                    <Modal.Body className="d-flex justify-content-center align-items-center flex-column py-5">
                        <div className="text-center p-3 w-75">
                            <h3 >{this.props.modalTitle}</h3>
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
                                onClick={onConfirm}
                                classes={classes.modalButton}
                            >
                                {this.props.customConfirmText ? this.props.customConfirmText : "Konfirmasi"}
                            </SirioButton>
                            <SirioButton
                                purple
                                circular
                                onClick={this.handleClose}
                                classes={classes.modalButton}
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