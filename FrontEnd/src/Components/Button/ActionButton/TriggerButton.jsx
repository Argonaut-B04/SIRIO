import React, { Component } from 'react';
import SirioButton from "../SirioButton";

export class TriggerButton extends Component {
    render() {
        const { disablePopUp, handleShow } = this.props;

        return (
            <SirioButton
                {...this.props}
                onClick={!disablePopUp && handleShow}
            >
                {this.props.children}
            </SirioButton>
        )
    }
}

export default TriggerButton
