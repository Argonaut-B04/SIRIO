import React, { Component } from 'react';
import SirioButton from "../Button/SirioButton";
import SirioAxiosBase from '../../Services/SirioAxiosBase';

class SirioDatePickerCustomInput extends Component {

    render() {
        var content = null;
        if (this.props.unchangedContent) {
            content = this.props.children;
        } else {
            content = this.props.value ? SirioAxiosBase.formatDateFromSirioDatePicker(this.props.value) : this.props.children;
        }

        return (
            <SirioButton
                onClick={this.props.onClick}
                {...this.props}
            >
                {content}
            </SirioButton>
        );
    }
}

export default SirioDatePickerCustomInput;