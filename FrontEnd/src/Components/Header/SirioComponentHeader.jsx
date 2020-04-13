import React, { Component } from 'react';
import classes from './SirioComponentHeader.module.css';

class SirioComponentHeader extends Component {
    render() {
        return (
            <div className={classes.headerWrapper}>
                {this.props.title ?
                    <div className="row">
                        <h2 className={classes.title}>
                            {this.props.title}
                        </h2>
                        {this.props.headerButton ?
                            <div className="ml-auto">{this.props.headerButton}</div>
                            :
                            ""
                        }
                    </div>
                    :
                    ""
                }
                {this.props.betweenTitleSubtitle ?
                    <div className="row">
                        {this.props.betweenTitleSubtitle}
                    </div>
                    :
                    ""
                }

                {this.props.subtitle ?
                    <div className="row">
                        <h5 className={classes.subtitle}>
                            {this.props.subtitle}
                        </h5>
                    </div>
                    :
                    ""
                }
            </div>
        );
    }
}

export default SirioComponentHeader;
