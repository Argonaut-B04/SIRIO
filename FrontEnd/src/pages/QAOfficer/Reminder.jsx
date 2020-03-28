import React, { Component } from 'react';
import { withRouter } from 'react-router-dom';

class Reminder extends Component {

    constructor(props) {
        super(props);

        this.state = {
            idRekomendasi: this.props.location.state ? this.props.location.state.id : null,
            directAccess: this.props.location.state ? true : false
        }
    }

    render() {
        return (
            <div>
                {!this.state.directAccess && <h1>You cant access this page directly</h1>}
                {this.state.idRekomendasi}
            </div>
        );
    }
}

export default withRouter(Reminder);
