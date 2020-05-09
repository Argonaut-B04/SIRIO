import React, { Component } from 'react';
import Modal from 'react-bootstrap/Modal';
import classes from "./ActionButton.module.css";

export class SirioModal extends Component {

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
        const { image, modalTitle, modalDesc, footerButton, show } = props;
        const { modalButton, modalDescWrapper } = classes;
        return (
            <Modal
                size="md"
                show={show}
                onHide={handleClose}
                centered>
                <Modal.Body className="d-flex justify-content-center align-items-center flex-column py-5">
                    {image}

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

                    {footerButton &&
                        <div className="d-flex justify-content-center align-items-center w-100">
                            {footerButton}
                        </div>
                    }
                </Modal.Body>
            </Modal>
        )
    }
}

export default SirioModal
