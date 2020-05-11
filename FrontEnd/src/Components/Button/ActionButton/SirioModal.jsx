import React, { Component } from 'react';
import Modal from 'react-bootstrap/Modal';
import classes from "./ActionButton.module.css";

export class SirioModal extends Component {

    render() {
        const { props } = this;
        const { image, modalTitle, modalDesc, footerButton, show, handleClose } = props;
        const { modalDescWrapper } = classes;
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
