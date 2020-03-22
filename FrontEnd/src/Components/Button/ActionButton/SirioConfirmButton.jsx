import React from "react";
import Modal from "react-bootstrap/Modal";
import SirioButton from "../SirioButton";
import "bootstrap/dist/css/bootstrap.min.css";
import classes from "./ActionButton.module.css";

export default class SirioWarningButton extends React.Component {

    constructor(props, context) {
        super(props, context);

        this.state = {
            show: false,
        };

        this.handleShow = this.handleShow.bind(this);
        this.handleClose = this.handleClose.bind(this);

    }

    handleClose() {
        this.setState({ show: false });
    }

    handleShow() {
        this.setState({ show: true });
    }

    render() {
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

                        <div className="d-flex justify-content-center align-items-center w-100">
                            <SirioButton
                                purple
                                recommended
                                circular
                                onClick={this.props.onConfirm ? this.props.onConfirm : this.handleClose}
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