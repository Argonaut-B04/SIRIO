import React, { Component } from 'react';

class SirioDropdownItem extends Component {
    render() {
        return (
            <div className={this.props.classes} onClick={() => this.props.onClick(this.props.clickArgument)} >
                {this.props.children}
            </div>
        );
    }
}

export default SirioDropdownItem;
