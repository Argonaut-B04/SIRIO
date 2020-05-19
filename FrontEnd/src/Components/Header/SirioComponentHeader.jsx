import React, { Component } from 'react';
import classes from './SirioComponentHeader.module.css';

class SirioComponentHeader extends Component {
    render() {
        const { headerWrapper } = classes;
        const { title, headerButton, betweenTitleSubtitle, subtitle } = this.props;

        return (
            <div className={headerWrapper}>
<<<<<<< HEAD
                {!!headerButton ?
                    <div className="row">
                        <div className="col-9">
=======
                <div className="row">
                    {!!headerButton ?
                        <>
                            <div className="col-9">
                                {title &&
                                    <h2 className={classes.title}>
                                        {title}
                                    </h2>
                                }
                                {betweenTitleSubtitle}
                                {subtitle &&
                                    <h5 className={classes.subtitle}>
                                        {subtitle}
                                    </h5>
                                }
                            </div>
                            <div className="col-3 d-flex justify-content-end align-items-center">
                                {headerButton && <div className="ml-auto">{headerButton}</div>}
                            </div>
                        </>
                        :
                        <>
>>>>>>> ed28f3989398298783774cfc5940d11f7fd72354
                            {title &&
                                <h2 className={classes.title}>
                                    {title}
                                </h2>
                            }
                            {betweenTitleSubtitle}
                            {subtitle &&
                                <h5 className={classes.subtitle}>
                                    {subtitle}
                                </h5>
                            }
<<<<<<< HEAD
                        </div>
                        <div className="col-3 d-flex justify-content-end align-items-center">
                            {headerButton && <div className="ml-auto">{headerButton}</div>}
                        </div>
                    </div>
                    :
                    <>
                        {title &&
                            <h2 className={classes.title}>
                                {title}
                            </h2>
                        }
                        {betweenTitleSubtitle}
                        {subtitle &&
                            <h5 className={classes.subtitle}>
                                {subtitle}
                            </h5>
                        }
                    </>
                }
=======
                        </>
                    }
                </div>
>>>>>>> ed28f3989398298783774cfc5940d11f7fd72354
            </div>
        );
    }
}

export default SirioComponentHeader;
