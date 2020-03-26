import React, { Component } from 'react';
import SirioButton from "../Button/SirioButton";

class SirioDatePickerCustomInput extends Component {

    render() {
        return (
            <SirioButton
                onClick={this.props.onClick}
                {...this.props}
            >
                {this.props.value ? this.props.value : this.props.children}
            </SirioButton>
        );
    }
}

export default SirioDatePickerCustomInput;